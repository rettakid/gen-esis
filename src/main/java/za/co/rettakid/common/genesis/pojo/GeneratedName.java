package za.co.rettakid.common.genesis.pojo;

import za.co.rettakid.common.genesis.common.Utilz;
import za.co.rettakid.common.genesis.enums.NamingStd;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public class GeneratedName {

    private String originalName;
    private String lowerCaseOrgName;
    private String lowerCaseName;
    private String camelCaseName;
    private String parcelCaseName;

    public GeneratedName(String originalName)  {
        this.originalName = originalName;
        this.lowerCaseOrgName = originalName.toLowerCase();
        this.camelCaseName = Utilz.convertTo(originalName.toLowerCase(), NamingStd.CAMEL);
        this.parcelCaseName = Utilz.convertTo(originalName.toLowerCase(), NamingStd.PARCEL);
        this.lowerCaseName = this.camelCaseName.toLowerCase();
    }

    public String getLowerCaseOrgName() {
        return lowerCaseOrgName;
    }

    public void setLowerCaseOrgName(String lowerCaseOrgName) {
        this.lowerCaseOrgName = lowerCaseOrgName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getLowerCaseName() {
        return lowerCaseName;
    }

    public void setLowerCaseName(String lowerCaseName) {
        this.lowerCaseName = lowerCaseName;
    }

    public String getCamelCaseName() {
        return camelCaseName;
    }

    public void setCamelCaseName(String camelCaseName) {
        this.camelCaseName = camelCaseName;
    }

    public String getParcelCaseName() {
        return parcelCaseName;
    }

    public void setParcelCaseName(String parcelCaseName) {
        this.parcelCaseName = parcelCaseName;
    }

}
