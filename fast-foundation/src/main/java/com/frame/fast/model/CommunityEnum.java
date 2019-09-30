package com.frame.fast.model;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.google.gson.annotations.SerializedName;

public enum CommunityEnum {

    @SerializedName("0")
    SENLIN425(0,"森林公馆（栖林路425弄）"),
    @SerializedName("1")
    SENLIN468(1,"森林公馆（栖林路468弄）"),
    @SerializedName("2")
    JINDI951(2,"金地格林世界（格林公馆）（芳林路951弄）"),
    @SerializedName("3")
    HONGLI560(3,"宏立瑞园（宝翔路560弄）"),
    @SerializedName("4")
    LANGSHI888(4,"朗诗绿色街区（宝翔路888弄）"),
    @SerializedName("5")
    SHANGJUAN998(5,"上隽嘉苑（公寓）（宝翔路998弄）"),
    @SerializedName("6")
    LUJIN288(6,"路劲翡丽湾（公寓）（雅翔路288弄）"),
    @SerializedName("7")
    SANGXIANG188(7,"三湘森林海尚（嘉定）（宝翔路188弄）"),
    @SerializedName("8")
    CHENGSHI302(8,"城市公馆（栖林路302弄）"),
    @SerializedName("9")
    HUPAN1280(9,"湖畔天下（瑞林路1280弄）"),
    @SerializedName("10")
    HUPAN800(10,"湖畔天下（瑞林路800弄）"),
    @SerializedName("11")
    LUER333(11,"卢尔公寓（芳林路333弄）"),
;
    @EnumValue
    private int value;

    private String name;

    CommunityEnum(int value,String name){
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CommunityEnum forValue(int value) {
        for (CommunityEnum sort : CommunityEnum.values()) {
            if (sort.getValue() == value) {
                return sort;
            }
        }
        return null;
    }

    public static CommunityEnum forName(String name) {
        for (CommunityEnum sort : CommunityEnum.values()) {
            if (sort.getName().equals(name)) {
                return sort;
            }
        }
        return null;
    }
}
