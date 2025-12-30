package controller;

import model.Session;
import view.view_ThoiKhoaBieu;

public class controller_ThoiKhoaBieu {

    private final view_ThoiKhoaBieu view;
    private final Session session;

    public controller_ThoiKhoaBieu(view_ThoiKhoaBieu view, Session session) {
        this.view = view;
        this.session = session;
    }
}
