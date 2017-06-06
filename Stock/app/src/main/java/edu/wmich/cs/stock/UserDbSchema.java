package edu.wmich.cs.stock;


public class UserDbSchema {
    public static final class UserTable {
        public static final String NAME = "users";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String username = "username";
            public static final String password = "password";
            public static final String email = "email";
        }
    }

    public static final class StockTable {
        public static final String NAME = "stock";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String userid = "userid";
            public static final String stock = "stock";
            public static final String quantity = "quantity";
            public static final String price = "price";
        }
    }
}
