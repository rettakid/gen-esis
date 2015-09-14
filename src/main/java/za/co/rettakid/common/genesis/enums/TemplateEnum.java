package za.co.rettakid.common.genesis.enums;

/**
 * Created by Lwazi Prusent on 2015/08/30.
 */
public enum TemplateEnum {

    JAVA_BASE_CLIENT("java\\BaseClient_java.vm"),
    JAVA_CLIENT("java\\TemplateClient_java.vm"),
    JAVA_DTO("java\\TemplateDto_java.vm"),
    JAVA_VO("java\\TemplateVo_java.vm"),
    JAVA_BINDING("java\\TemplateBind_java.vm"),
    JAVA_LIST_DTO("java\\TemplateListDto_java.vm"),
    PHP_BINDING("php\\TemplateBinding_php.vm"),
    PHP_CONFIG("php\\config_php.vm"),
    PHP_BOOTSTRAP("php\\bootstrap_php.vm"),
    PHP_ENTITY("php\\TemplateEntity_php.vm"),
    PHP_DTO("php\\TemplateDto_php.vm"),
    PHP_BASE_DTO("php\\Dto_php.vm"),
    PHP_CONTROLLER("php\\TemplateController_php.vm"),
    PHP_XML("php\\xml_php.vm");

    private String location;

    TemplateEnum(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
