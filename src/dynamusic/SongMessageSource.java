package dynamusic;

import atg.dms.patchbay.MessageSource;
import atg.dms.patchbay.MessageSourceContext;
import atg.nucleus.GenericService;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * This component fires a NewSongMessage event using JMS.
 * <p>
 * Note that since it makes use of DMS and PatchBay it does not have any knowledge
 * of where the message is being sent.  See the dynamoMessagingSystem.xml file
 * for the destination of these messages (ultimately they will go to the ScenarioManager
 * component).
 */
public class SongMessageSource extends GenericService implements MessageSource {
    private MessageSourceContext mContext;
    private boolean mStarted = false;
    
    // These methods implement the MessageSource interface
    public void setMessageSourceContext(MessageSourceContext pContext) {
        mContext = pContext;
    }
    
    public void startMessageSource() {
        mStarted = true;
    }
    
    public void stopMessageSource() {
        mStarted = false;
    }
    
    // This method will send a message
    public void fireMessage(String pSongId, String pSongGenre, String pTitle) throws JMSException {
        if (mStarted) {
            
            NewSongMessage newSongMessage = new NewSongMessage(pSongId, pSongGenre, pTitle);
            
            ObjectMessage objectMessage = mContext.createObjectMessage();
            objectMessage.setJMSType("NewSongMessage");
            objectMessage.setObject(newSongMessage);
            if (isLoggingDebug()) {
                logDebug("SongMessageSource sending message: " + objectMessage);
            }
            mContext.sendMessage(objectMessage);
        } else {
            if (isLoggingDebug()) {
                logDebug("fire message called but message source not yet started");
            }
        }
    }
}