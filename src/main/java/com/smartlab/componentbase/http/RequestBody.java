package com.smartlab.componentbase.http;

/**
 * @author：xxl
 * @Created in：2019/6/5
 */
public class RequestBody {
    /**
     * cmd : findUserById
     * type : request
     * request : {"id":133}
     */

    private String cmd;
    private String type;
    private RequestBean request;
    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RequestBean getRequest() {
        return request;
    }

    public void setRequest(RequestBean request) {
        this.request = request;
    }

    public static class RequestBean {
        /**
         * id : 133
         */

        private int id;
        private int userId;
        private String count;
        private String password;

        public String getCount() {
            return count;
        }

        public RequestBean setCount(String count) {
            this.count = count;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public RequestBean setPassword(String password) {
            this.password = password;
            return this;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public RequestBean setUserId(int userId) {
            this.userId = userId;
            return this;
        }
    }
}
