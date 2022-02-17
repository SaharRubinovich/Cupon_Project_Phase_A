package Jobs;

import db.DbCouponManager;
import db.DbUtils;
import dbdao.CouponsDbDao;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDbDao couponsDbDao = new CouponsDbDao();
    private boolean isRunning;

    public CouponExpirationDailyJob() {
        setRunning(true);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void stop(){
        setRunning(false);
    }

    @Override
    public void run() {
        while (isRunning) {
            System.out.println("Daily clean-up started!");
            DbUtils.runQuery(DbCouponManager.EXPIRE_COUPONS_DELETE);
            try {
                Thread.sleep(1000 * 60 * 60 * 24);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
