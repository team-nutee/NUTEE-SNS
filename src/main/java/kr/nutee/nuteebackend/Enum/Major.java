package kr.nutee.nuteebackend.Enum;

public enum Major {
    SOCIOLOGY("SOCIOLOGY"),
    SOCIAL_SCIENCE("SOCIAL_SCIENCE"),
    ECONOMY("ECONOMY"),
    POLITICS("POLITICS"),
    BUSINESS_ADMIN("BUSINESS_ADMIN"),
    WELFARE("WELFARE"),
    IT("IT"),
    INFO_COM("INFO_COM"),
    SOFTWARE("SOFTWARE"),
    COMPUTER_SCIENCE("COMPUTER_SCIENCE"),
    GLOCAL_IT("GLOCAL_IT"),
    HUMANITIES("HUMANITIES"),
    ENGLISH("ENGLISH"),
    JAPANESE("JAPANESE"),
    CHINESE("CHINESE"),
    MISSION("MISSION"),
    MEDIA_CONTENTS("MEDIA_CONTENTS"),
    JOURNALISM("JOURNALISM"),
    DIGITAL_CONTENTS("DIGITAL_CONTENTS");
    public String major;

    Major(String major) {
        this.major = major;
    }

}
