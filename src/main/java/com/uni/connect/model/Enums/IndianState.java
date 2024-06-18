package com.uni.connect.model.Enums;

public enum IndianState {
    ANDHRA_PRADESH("Andhra Pradesh"),
    ARUNACHAL_PRADESH("Arunachal Pradesh"),
    ASSAM("Assam"),
    BIHAR("Bihar"),
    CHHATTISGARH("Chhattisgarh"),
    GOA("Goa"),
    GUJARAT("Gujarat"),
    HARYANA("Haryana"),
    HIMACHAL_PRADESH("Himachal Pradesh"),
    JHARKHAND("Jharkhand"),
    KARNATAKA("Karnataka"),
    KERALA("Kerala"),
    MADHYA_PRADESH("Madhya Pradesh"),
    MAHARASHTRA("Maharashtra"),
    MANIPUR("Manipur"),
    MEGHALAYA("Meghalaya"),
    MIZORAM("Mizoram"),
    NAGALAND("Nagaland"),
    ODISHA("Odisha"),
    PUNJAB("Punjab"),
    RAJASTHAN("Rajasthan"),
    SIKKIM("Sikkim"),
    TAMIL_NADU("Tamil Nadu"),
    TELANGANA("Telangana"),
    TRIPURA("Tripura"),
    UTTARAKHAND("Uttarakhand"),
    UTTAR_PRADESH("Uttar Pradesh"),
    WEST_BENGAL("West Bengal"),
    ANDAMAN_AND_NICOBAR("Andaman and Nicobar Islands"),
    CHANDIGARH("Chandigarh"),
    DADRA_AND_NAGAR_HAVELI("Dadra and Nagar Haveli"),
    DAMAN_AND_DIU("Daman and Diu"),
    DELHI("Delhi"),
    LAKSHADWEEP("Lakshadweep"),
    PUDUCHERRY("Puducherry");

    private final String displayName;

    IndianState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
