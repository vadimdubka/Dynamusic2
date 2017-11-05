package dynamusic;

import atg.repository.RepositoryItemImpl;
import atg.repository.RepositoryPropertyDescriptor;

import java.util.Date;

public class FindAge extends RepositoryPropertyDescriptor {
    private String ageFormat = "years";
    /**
     * This will be the name of attribute that is set in the XML repository definition file to control the age format.
     */
    private final static String AGE_FORMAT_ATTR_NAME = "ageFormat";
    
    @Override
    public Object getPropertyValue(RepositoryItemImpl pItem, Object pValue) {
        if (pValue != null) {
            return pValue;
        }
        
        Date dateOfBirth = (Date) pItem.getPropertyValue("dateOfBirth");
        if (dateOfBirth != null) {
            if (ageFormat.equalsIgnoreCase("years")) {
                return AgeCalc.ageInYears(dateOfBirth);
            } else {
                return AgeCalc.ageInDays(dateOfBirth);
            }
        }
        
        return -1;
    }
    
    
    /**
     * Put in a setValue to allow us to load in config
     * info from an <attribute...> tag in the definition file.
     **/
    @Override
    public void setValue(String pAttributeName, Object pValue) {
        super.setValue(pAttributeName, pValue);
        
        if (pAttributeName.equalsIgnoreCase(AGE_FORMAT_ATTR_NAME)) {
            ageFormat = pValue.toString();
        }
    }
    
    @Override
    public Object getValue(String attributeName) {
        return super.getValue(attributeName);
    }
    
    /**
     * Returns property Queryable - this needs to be false
     * because this property is not stored in the database.
     **/
    @Override
    public boolean isQueryable() {
        return false;
    }
    
    /**
     * Returns property Writable. - this property is always false
     * because it doesn't store a value; it only returns a formatted
     * version of values stored in other properties.
     */
    @Override
    public boolean isWritable() {
        return false;
    }
}
