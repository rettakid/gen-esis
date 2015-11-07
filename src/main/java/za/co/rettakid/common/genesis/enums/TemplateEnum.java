package za.co.rettakid.common.genesis.enums;

/**
 * Created by Lwazi Prusent on 2015/08/30.
 */
public enum TemplateEnum {

    ANDROID_BASE_CLIENT("android\\BaseClient_java.vm"),
    ANDROID_CLIENT("android\\TemplateClient_java.vm"),
    ANDROID_DTO("android\\TemplateDto_java.vm"),
    ANDROID_LIST_DTO("android\\TemplateListDto_java.vm"),
    ANDROID_VO("android\\TemplateVo_java.vm"),
    ANDROID_BINDING("android\\TemplateBind_java.vm"),

    JAVA_DTO("java\\TemplateDto_java.vm"),
    JAVA_DTO_LIST("java\\TemplateListDto_java.vm"),
    JAVA_ENTITY("java\\TemplateEntity_java.vm"),
    JAVA_DAO("java\\TemplateDao_java.vm"),
    JAVA_DAO_IMPL("java\\TemplateDaoImpl_java.vm"),
    JAVA_SERVICE("java\\TemplateService_java.vm"),
    JAVA_SERVICE_IMPL("java\\TemplateServiceImpl_java.vm"),
    JAVA_ENTITY_BINDING("java\\TemplateEntityBind_java.vm"),
    JAVA_VO("java\\TemplateVo_java.vm"),
    JAVA_VO_BINDING("java\\TemplateVoBind_java.vm"),
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
