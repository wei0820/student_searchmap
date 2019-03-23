package com.student.student_searchmap.Data;

public class GoogleMapPlaceDetailsData {
    public  String status;
    public Result result;

    public class Result{

        public String formatted_address;
        public String formatted_phone_number;
        public Geometry geometry;
        public String id;
        public String name;
        public Opening_hours opening_hours;
        public Photos[] photos;
        public Reviews[] reviews;
        public float rating;
        public String place_id;





        public  class  Geometry{

            public  Location location;

            public class  Location{
                public Double lat;
                public Double lng;
            }


        }
        public class Photos{
            public String photo_reference;

            public String height;

            public String[] html_attributions;

            public String width;
        }
        public class Reviews {
            public long time;

            public String text;

            public String profile_photo_url;

            public String relative_time_description;

            public String author_url;

            public String author_name;

            public float rating;

            public String language;

        }
        public  class Opening_hours{

            public boolean open_now;
            public Periods[] periods;
            public  String[]weekday_text;
            public class Periods{
                public Close close;
                public Opebn open;

                public class  Close{
                    public int day;
                    public String time;

                }
                public class  Opebn{
                    public int day;
                    public String time;
                }
            }



        }


    }
}
