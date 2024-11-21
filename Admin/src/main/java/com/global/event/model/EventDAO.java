package com.global.event.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EventDAO {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private String sql = null;

    private static EventDAO instance = null;

    public static EventDAO getInstance() {
        if (instance == null)
            instance = new EventDAO();
        return instance;
    }

    public void openConn() {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/myoracle");
            conn = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null && !rs.isClosed())
                rs.close();
            if (pstmt != null && !pstmt.isClosed())
                pstmt.close();
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 이벤트 수 가져오기
    public int getEventCount(String eventType) {
        int count = 0;
        try {
            openConn();
            if ("coupon".equalsIgnoreCase(eventType)) {
            	sql = "SELECT count(*) FROM COUPON_EVENT JOIN EVENT USING(EVENT_NO) "
                        + "LEFT JOIN EVENT_IMAGE ON COUPON_EVENT.IMAGE_NO = EVENT_IMAGE.IMAGE_NO";
            } else if ("banner".equalsIgnoreCase(eventType)) {
            	sql = "SELECT count(*) FROM BANNER_EVENT JOIN EVENT USING(EVENT_NO) "
                        + "LEFT JOIN EVENT_IMAGE ON BANNER_EVENT.IMAGE_NO = EVENT_IMAGE.IMAGE_NO";
            } else {
            	 sql = "SELECT count(*) FROM EVENT LEFT JOIN EVENT_IMAGE USING(EVENT_NO)";
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return count;
    }

 // 이벤트 리스트 가져오기
    public List<? extends Event> getEventList(String eventType) {
        List<? extends Event> eventList = new ArrayList<>();
        
        try {
            openConn();

            if ("coupon".equalsIgnoreCase(eventType)) {
                sql = "SELECT * FROM COUPON_EVENT JOIN EVENT USING(EVENT_NO) "
                    + "LEFT JOIN EVENT_IMAGE ON COUPON_EVENT.IMAGE_NO = EVENT_IMAGE.IMAGE_NO";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                List<CouponEvent> couponEventList = new ArrayList<>();

                while (rs.next()) {
                    CouponEvent couponEvent = new CouponEvent();
                    populateCommonEventFields(couponEvent, rs);
                    couponEvent.setCouponCode(rs.getString("COUPON_CODE"));
                    couponEvent.setDiscountAmount(rs.getDouble("DISCOUNT_AMOUNT"));
                    couponEvent.setDiscountPercent(rs.getDouble("DISCOUNT_PERCENT"));
                    couponEvent.setExpiryDate(rs.getDate("EXPIRY_DATE"));

                    // EventImage 처리
                    EventImage eventImage = populateEventImage(rs);
                    couponEvent.setEventImage(eventImage);

                    couponEventList.add(couponEvent);
                }
                return couponEventList;

            } else if ("banner".equalsIgnoreCase(eventType)) {
                sql = "SELECT * FROM BANNER_EVENT JOIN EVENT USING(EVENT_NO) "
                    + "LEFT JOIN EVENT_IMAGE ON BANNER_EVENT.IMAGE_NO = EVENT_IMAGE.IMAGE_NO";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                List<BannerEvent> bannerEventList = new ArrayList<>();

                while (rs.next()) {
                    BannerEvent bannerEvent = new BannerEvent();
                    populateCommonEventFields(bannerEvent, rs);
                    bannerEvent.setLinkUrl(rs.getString("LINK_URL"));
                    bannerEvent.setDisplayStartDate(rs.getDate("DISPLAY_START_DATE"));
                    bannerEvent.setDisplayEndDate(rs.getDate("DISPLAY_END_DATE"));

                    // EventImage 처리
                    EventImage eventImage = populateEventImage(rs);
                    bannerEvent.setEventImage(eventImage);

                    bannerEventList.add(bannerEvent);
                }
                return bannerEventList;

            } else {
                sql = "SELECT * FROM EVENT LEFT JOIN EVENT_IMAGE USING(EVENT_NO)";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                List<Event> generalEventList = new ArrayList<>();

                while (rs.next()) {
                    Event event = new Event();
                    populateCommonEventFields(event, rs);

                    // EventImage 처리
                    EventImage eventImage = populateEventImage(rs);
                    event.setEventImage(eventImage);

                    generalEventList.add(event);
                }
                return generalEventList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return eventList;
    }


    // 공통 이벤트 필드를 채우는 헬퍼 메서드
    private void populateCommonEventFields(Event event, ResultSet rs) throws SQLException {
        event.setEventNo(rs.getInt("EVENT_NO"));
        event.setName(rs.getString("NAME"));
        event.setDescription(rs.getString("DESCRIPTION"));
        event.setViews(rs.getInt("VIEWS"));
        event.setStartDate(rs.getDate("START_DATE"));
        event.setEndDate(rs.getDate("END_DATE"));
        event.setCreatedAt(rs.getDate("CREATED_AT"));
        event.setUpdatedAt(rs.getDate("UPDATED_AT"));
        event.setStatus(rs.getString("STATUS"));
        event.setEventType(rs.getString("EVENT_TYPE"));
    }

 // EventImage를 생성하는 헬퍼 메서드
    private EventImage populateEventImage(ResultSet rs) throws SQLException {
        // 이미지 데이터가 존재하는지 체크 (IMAGE_NO가 null이 아니고 0이 아닐 때)
        Integer imageNo = rs.getObject("IMAGE_NO") != null ? rs.getInt("IMAGE_NO") : null;
        if (imageNo != null) {
            EventImage eventImage = new EventImage();
            eventImage.setImageId(rs.getInt("IMAGE_NO"));
            eventImage.setFilePath(rs.getString("FILE_PATH"));
            eventImage.setFileName(rs.getString("FILE_NAME"));
            eventImage.setFileSize(rs.getInt("FILE_SIZE"));
            eventImage.setCreatedAt(rs.getDate("CREATED_AT"));
            eventImage.setUpdatedAt(rs.getDate("UPDATED_AT"));
            return eventImage;
        }
        return null;  // 이미지가 없을 경우 null 반환
    }

    public Event getEventDetail(int eventNo, String eventType) {
        Event event = null;
        try {
            openConn();
            String sql = "";
            if ("COUPON".equalsIgnoreCase(eventType)) {
                sql = "SELECT * FROM COUPON_EVENT JOIN EVENT ON COUPON_EVENT.EVENT_NO = EVENT.EVENT_NO "
                        + "LEFT JOIN EVENT_IMAGE ON COUPON_EVENT.EVENT_NO = EVENT_IMAGE.EVENT_NO WHERE COUPON_EVENT.EVENT_NO = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, eventNo);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    event = new CouponEvent();  // 쿠폰 이벤트 객체 생성
                    populateCommonEventFields(event, rs);

                    // 쿠폰 이벤트 전용 필드 설정
                    ((CouponEvent) event).setCouponCode(rs.getString("COUPON_CODE"));
                    ((CouponEvent) event).setDiscountAmount(rs.getDouble("DISCOUNT_AMOUNT"));
                    ((CouponEvent) event).setDiscountPercent(rs.getDouble("DISCOUNT_PERCENT"));
                    ((CouponEvent) event).setExpiryDate(rs.getDate("EXPIRY_DATE"));

                    // EventImage 처리
                    EventImage eventImage = populateEventImage(rs);
                    event.setEventImage(eventImage);
                }

            } else if ("BANNER".equalsIgnoreCase(eventType)) {
                sql = "SELECT * FROM BANNER_EVENT JOIN EVENT ON BANNER_EVENT.EVENT_NO = EVENT.EVENT_NO "
                        + "LEFT JOIN EVENT_IMAGE ON BANNER_EVENT.EVENT_NO = EVENT_IMAGE.EVENT_NO WHERE BANNER_EVENT.EVENT_NO = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, eventNo);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    event = new BannerEvent();  // 배너 이벤트 객체 생성
                    populateCommonEventFields(event, rs);

                    // 배너 이벤트 전용 필드 설정
                    ((BannerEvent) event).setLinkUrl(rs.getString("LINK_URL"));
                    ((BannerEvent) event).setDisplayStartDate(rs.getDate("DISPLAY_START_DATE"));
                    ((BannerEvent) event).setDisplayEndDate(rs.getDate("DISPLAY_END_DATE"));

                    // EventImage 처리
                    EventImage eventImage = populateEventImage(rs);
                    event.setEventImage(eventImage);
                }

            } else {
                // 일반 이벤트 처리
                sql = "SELECT * FROM EVENT LEFT JOIN EVENT_IMAGE USING(EVENT_NO) WHERE EVENT_NO = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, eventNo);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    event = new Event();  // 일반 이벤트 객체 생성
                    populateCommonEventFields(event, rs);

                    // EventImage 처리
                    EventImage eventImage = populateEventImage(rs);
                    event.setEventImage(eventImage);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return event;
    }


    
    public int insertEvent(Event event) {
        int eventNo = 0;
        try {
            openConn();

            // Step 1: 테이블의 최대 PK 값 조회
            sql = "SELECT MAX(EVENT_NO) FROM EVENT";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int maxEventNo = 0;
            if (rs.next()) {
                maxEventNo = rs.getInt(1);
            }

            // Step 2: 시퀀스의 현재 값 확인
            String seqValueSql = "SELECT SEQ_EVENT_NO.NEXTVAL FROM dual";
            pstmt = conn.prepareStatement(seqValueSql);
            rs = pstmt.executeQuery();
            int currentSeqValue = 0;
            if (rs.next()) {
                currentSeqValue = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            // Step 3: 시퀀스 값이 테이블의 최대 PK보다 작은 경우, 시퀀스 삭제 후 재생성
            if (currentSeqValue <= maxEventNo) {
                String dropSeqSql = "DROP SEQUENCE SEQ_EVENT_NO";
                pstmt = conn.prepareStatement(dropSeqSql);
                pstmt.executeUpdate();
                pstmt.close();

                String createSeqSql = "CREATE SEQUENCE SEQ_EVENT_NO START WITH " + (maxEventNo + 1);
                pstmt = conn.prepareStatement(createSeqSql);
                pstmt.executeUpdate();
                pstmt.close();
            }

            // Step 4: 이벤트 삽입
            sql = "INSERT INTO EVENT (EVENT_NO, NAME, DESCRIPTION, START_DATE, END_DATE, EVENT_TYPE) "
                + "VALUES (SEQ_EVENT_NO.NEXTVAL, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, new String[] {"EVENT_NO"});
            pstmt.setString(1, event.getName());
            pstmt.setString(2, event.getDescription());
            pstmt.setDate(3, event.getStartDate());
            pstmt.setDate(4, event.getEndDate());
            pstmt.setString(5, event.getEventType());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                eventNo = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return eventNo;
    }

    public int insertBannerEvent(BannerEvent bannerEvent) {
    	int res = 0;
        try {
            openConn();

            // Step 1: 테이블의 최대 PK 값 조회
            sql = "SELECT MAX(BANNER_EVENT_NO) FROM BANNER_EVENT";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int maxBannerNo = 0;
            if (rs.next()) {
                maxBannerNo = rs.getInt(1);
            }

            // Step 2: 시퀀스의 현재 값 확인
            String seqValueSql = "SELECT SEQ_BANNER_EVENT_NO.NEXTVAL FROM DUAL";
            pstmt = conn.prepareStatement(seqValueSql);
            rs = pstmt.executeQuery();
            int currentSeqValue = 0;
            if (rs.next()) {
                currentSeqValue = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            // Step 3: 시퀀스 값이 테이블의 최대 PK보다 작은 경우, 시퀀스 삭제 후 재생성
            if (currentSeqValue <= maxBannerNo) {
                String dropSeqSql = "DROP SEQUENCE SEQ_BANNER_EVENT_NO";
                pstmt = conn.prepareStatement(dropSeqSql);
                pstmt.executeUpdate();
                pstmt.close();

                String createSeqSql = "CREATE SEQUENCE SEQ_BANNER_EVENT_NO START WITH " + (maxBannerNo + 1);
                pstmt = conn.prepareStatement(createSeqSql);
                pstmt.executeUpdate();
                pstmt.close();
            }

            // Step 4: 배너 이벤트 삽입
            sql = "INSERT INTO BANNER_EVENT (BANNER_EVENT_NO, EVENT_NO, LINK_URL) "
                + "VALUES (SEQ_BANNER_EVENT_NO.NEXTVAL, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bannerEvent.getEventNo());
            pstmt.setString(2, bannerEvent.getLinkUrl());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        
        return res;
    }

    public int insertCouponEvent(CouponEvent couponEvent) {
    	int res = 0;
        try {
            openConn();

            // Step 1: 테이블의 최대 PK 값 조회
            sql = "SELECT MAX(COUPON_EVENT_NO) FROM COUPON_EVENT";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int maxCouponNo = 0;
            if (rs.next()) {
                maxCouponNo = rs.getInt(1);
            }

            // Step 2: 시퀀스의 현재 값 확인
            String seqValueSql = "SELECT SEQ_COUPON_EVENT_NO.NEXTVAL FROM DUAL";
            pstmt = conn.prepareStatement(seqValueSql);
            rs = pstmt.executeQuery();
            int currentSeqValue = 0;
            if (rs.next()) {
                currentSeqValue = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            // Step 3: 시퀀스 값이 테이블의 최대 PK보다 작은 경우, 시퀀스 삭제 후 재생성
            if (currentSeqValue <= maxCouponNo) {
                String dropSeqSql = "DROP SEQUENCE SEQ_COUPON_EVENT_NO";
                pstmt = conn.prepareStatement(dropSeqSql);
                pstmt.executeUpdate();
                pstmt.close();

                String createSeqSql = "CREATE SEQUENCE SEQ_COUPON_EVENT_NO START WITH " + (maxCouponNo + 1);
                pstmt = conn.prepareStatement(createSeqSql);
                pstmt.executeUpdate();
                pstmt.close();
            }

            // Step 4: 쿠폰 이벤트 삽입
            sql = "INSERT INTO COUPON_EVENT (COUPON_EVENT_NO, EVENT_NO, COUPON_CODE, DISCOUNT_AMOUNT, DISCOUNT_PERCENT, EXPIRY_DATE) "
                + "VALUES (SEQ_COUPON_EVENT_NO.NEXTVAL, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, couponEvent.getEventNo());
            pstmt.setString(2, couponEvent.getCouponCode());
            pstmt.setDouble(3, couponEvent.getDiscountAmount());
            pstmt.setDouble(4, couponEvent.getDiscountPercent());
            pstmt.setDate(5, couponEvent.getExpiryDate());
           res = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return res;
    }


    public boolean isCouponCodeExists(String couponCode) {
        boolean exists = false;
        
        try {
            // 연결 열기
        	openConn();  // openConn() 메서드를 static이 아니므로 getInstance() 사용
        	sql = "SELECT COUNT(*) FROM COUPON_EVENT WHERE COUPON_CODE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, couponCode);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);  // 첫 번째 컬럼의 값을 가져옴
                if (count > 0) {
                    exists = true;  // 쿠폰 코드가 존재하면 true
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            closeConn(rs, pstmt, conn);
        }
        
        return exists;
    }





}
