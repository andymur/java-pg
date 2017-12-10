package com.andymur.pg.java.javafx.mvvm.base;

/**
 * Created by andymur on 12/10/17.
 */
public abstract class BaseView<VM extends BaseViewModel> {

    private final VM viewModel;

    public BaseView(VM viewModel) {
        this.viewModel = viewModel;
    }

    public VM getModel() {
        return viewModel;
    }
}
