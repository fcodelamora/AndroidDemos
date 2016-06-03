package edu.delamora.demo.gurunaviplaces.models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by delamora.
 */
public class GuruPlace implements Serializable {
//  App generated values

    //TODO semi-cached images
    private Bitmap  bitmap_image_url__shop_image1;
    private Bitmap  bitmap_image_url__shop_image2;

//  Web service values
    private String	id;
    private String	name;
    private String	name_kana;
    private double	latitude;
    private double	longitude;
    private  String	category;
    private String	url;
    private String	url_mobile;

    //image_url, split for simplicity
    private String image_url__shop_image1;
    private String image_url__shop_image2;
    private String image_url__qrcode;

    private String	address;
    private String	tel;
    private String	tel_sub;
    private String	fax;
    private String	opentime;
    private String	 holiday;

    //access, split for simplicity
    private String	access__line;
    private String	access__station;
    private String	access__station_exit;
    private int	    access__walk;
    private String	access__note;

    private int	parking_lots;

    //PR, split for simplicity
    private String pr__pr_short;
    private String pr__pr_long;

    private int	budget;
    private int	party;
    private int	lunch;
    private String	credit_card;
    private String	e_money;

    //flags, split for simplicity
    private String flags__mobile_site;
    private String flags__mobile_coupon;
    private String flags__pc_coupon;

    public GuruPlace(Bitmap bitmap_image_url__shop_image1, Bitmap bitmap_image_url__shop_image2, String id, String name, String name_kana, double latitude, double longitude, String category, String url, String url_mobile, String image_url__shop_image1, String image_url__shop_image2, String image_url__qrcode, String address, String tel, String tel_sub, String fax, String opentime, String holiday, String access__line, String access__station, String access__station_exit, int access__walk, String access__note, int parking_lots, String pr__pr_short, String pr__pr_long, int budget, int party, int lunch, String credit_card, String e_money, String flags__mobile_site, String flags__mobile_coupon, String flags__pc_coupon) {
        this.bitmap_image_url__shop_image1 = bitmap_image_url__shop_image1;
        this.bitmap_image_url__shop_image2 = bitmap_image_url__shop_image2;
        this.id = id;
        this.name = name;
        this.name_kana = name_kana;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.url = url;
        this.url_mobile = url_mobile;
        this.image_url__shop_image1 = image_url__shop_image1;
        this.image_url__shop_image2 = image_url__shop_image2;
        this.image_url__qrcode = image_url__qrcode;
        this.address = address;
        this.tel = tel;
        this.tel_sub = tel_sub;
        this.fax = fax;
        this.opentime = opentime;
        this.holiday = holiday;
        this.access__line = access__line;
        this.access__station = access__station;
        this.access__station_exit = access__station_exit;
        this.access__walk = access__walk;
        this.access__note = access__note;
        this.parking_lots = parking_lots;
        this.pr__pr_short = pr__pr_short;
        this.pr__pr_long = pr__pr_long;
        this.budget = budget;
        this.party = party;
        this.lunch = lunch;
        this.credit_card = credit_card;
        this.e_money = e_money;
        this.flags__mobile_site = flags__mobile_site;
        this.flags__mobile_coupon = flags__mobile_coupon;
        this.flags__pc_coupon = flags__pc_coupon;
    }
    // Get/sets
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getBitmap_image_url__shop_image1() {
        return bitmap_image_url__shop_image1;
    }

    public void setBitmap_image_url__shop_image1(Bitmap bitmap_image_url__shop_image1) {
        this.bitmap_image_url__shop_image1 = bitmap_image_url__shop_image1;
    }

    public Bitmap getBitmap_image_url__shop_image2() {
        return bitmap_image_url__shop_image2;
    }

    public void setBitmap_image_url__shop_image2(Bitmap bitmap_image_url__shop_image2) {
        this.bitmap_image_url__shop_image2 = bitmap_image_url__shop_image2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_kana() {
        return name_kana;
    }

    public void setName_kana(String name_kana) {
        this.name_kana = name_kana;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_mobile() {
        return url_mobile;
    }

    public void setUrl_mobile(String url_mobile) {
        this.url_mobile = url_mobile;
    }

    public String getImage_url__shop_image1() {
        return image_url__shop_image1;
    }

    public void setImage_url__shop_image1(String image_url__shop_image1) {
        this.image_url__shop_image1 = image_url__shop_image1;
    }

    public String getImage_url__shop_image2() {
        return image_url__shop_image2;
    }

    public void setImage_url__shop_image2(String image_url__shop_image2) {
        this.image_url__shop_image2 = image_url__shop_image2;
    }

    public String getImage_url__qrcode() {
        return image_url__qrcode;
    }

    public void setImage_url__qrcode(String image_url__qrcode) {
        this.image_url__qrcode = image_url__qrcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel_sub() {
        return tel_sub;
    }

    public void setTel_sub(String tel_sub) {
        this.tel_sub = tel_sub;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getAccess__line() {
        return access__line;
    }

    public void setAccess__line(String access__line) {
        this.access__line = access__line;
    }

    public String getAccess__station() {
        return access__station;
    }

    public void setAccess__station(String access__station) {
        this.access__station = access__station;
    }

    public String getAccess__station_exit() {
        return access__station_exit;
    }

    public void setAccess__station_exit(String access__station_exit) {
        this.access__station_exit = access__station_exit;
    }

    public int getAccess__walk() {
        return access__walk;
    }

    public void setAccess__walk(int access__walk) {
        this.access__walk = access__walk;
    }

    public String getAccess__note() {
        return access__note;
    }

    public void setAccess__note(String access__note) {
        this.access__note = access__note;
    }

    public int getParking_lots() {
        return parking_lots;
    }

    public void setParking_lots(int parking_lots) {
        this.parking_lots = parking_lots;
    }

    public String getPr__pr_short() {
        return pr__pr_short;
    }

    public void setPr__pr_short(String pr__pr_short) {
        this.pr__pr_short = pr__pr_short;
    }

    public String getPr__pr_long() {
        return pr__pr_long;
    }

    public void setPr__pr_long(String pr__pr_long) {
        this.pr__pr_long = pr__pr_long;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public int getLunch() {
        return lunch;
    }

    public void setLunch(int lunch) {
        this.lunch = lunch;
    }

    public String getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(String credit_card) {
        this.credit_card = credit_card;
    }

    public String getE_money() {
        return e_money;
    }

    public void setE_money(String e_money) {
        this.e_money = e_money;
    }

    public String getFlags__mobile_site() {
        return flags__mobile_site;
    }

    public void setFlags__mobile_site(String flags__mobile_site) {
        this.flags__mobile_site = flags__mobile_site;
    }

    public String getFlags__mobile_coupon() {
        return flags__mobile_coupon;
    }

    public void setFlags__mobile_coupon(String flags__mobile_coupon) {
        this.flags__mobile_coupon = flags__mobile_coupon;
    }

    public String getFlags__pc_coupon() {
        return flags__pc_coupon;
    }

    public void setFlags__pc_coupon(String flags__pc_coupon) {
        this.flags__pc_coupon = flags__pc_coupon;
    }

    //TODO add formatted getters to reduce ifs on other classes

    public String getFormattedAccess(boolean useLine, boolean useStation, boolean useStationExit, boolean useNote){
        String formattedAccess = "";

        if(useLine && ("{}".compareTo(this.access__line) != 0) &&
                ("".compareTo(this.access__line)!=0)){
            formattedAccess+=this.access__line;
        }
        if(useStation && ("{}".compareTo(this.access__station) != 0) &&
                ("".compareTo(this.access__station)!=0)){
            formattedAccess+=this.access__station;
        }
        if(useStationExit && ("{}".compareTo(this.access__station_exit) != 0) &&
                ("".compareTo(this.access__station_exit)!=0)){
            formattedAccess+=this.access__station_exit;
        }
        if(useNote && ("{}".compareTo(this.access__note) != 0) &&
                ("".compareTo(this.access__note)!=0)){
            formattedAccess+=". "+this.access__note;
        }
        if(formattedAccess.compareTo("") == 0){
            return null;
        }else{
            return formattedAccess;
        }
    }
    //TODO need to remove this functions from here and put them on UTILS
    //Check for any "empty" values, return null if empty
    public String getFormattedImage_url__shop_image(String shop_image_url){
        if (shop_image_url != null &&
                shop_image_url.compareTo("") != 0 &&
                shop_image_url.compareTo("{}") != 0){
            return shop_image_url;
        }else{
            return null;
        }
    }
    public String getFormattedLongText(String longText){
        if(longText != null &&
                longText.compareTo("")   != 0 &&
                longText.compareTo("{}") != 0){
            return longText.replace("<BR>","\n");
        }else{
            return null;
        }

    }
}
