package com.xtelsolution.xmec.presenter;

import android.text.TextUtils;

import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtelsolution.xmec.callbacks.ICommand;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.RESP_ListFriendActive;
import com.xtelsolution.xmec.model.RESP_List_Medical;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.model.UserModel;
import com.xtelsolution.xmec.views.inf.IHomeView;

/**
 * Created by phimau on 2/15/2017.
 */

public class HomePresenter extends BasePresenter {
    private static final String TAG = "HomePresenter";
    private final int GETUSER = 1;
    private final int GETMEDICAL = 2;
    private final int GetFriend = 3;
    private final int GetMedicalUid = 4;
    private final int DeleteFriend = 5;
    private IHomeView view;
    private ICommand iCmd = new ICommand() {
        @Override
        public void excute(Object... params) {
            switch ((int) params[0]) {

                /*
                 * Get Friend*/
                case 1:
                    UserModel.getintance().getFriendList(LoginManager.getCurrentSession(), new ResponseHandle<RESP_ListFriendActive>(RESP_ListFriendActive.class) {
                        @Override
                        public void onSuccess(RESP_ListFriendActive obj) {
                            view.onGetFriendActiveSuccess(obj.getData());
                        }

                        @Override
                        public void onError(Error error) {
                            if (error.getCode() == 2) {
                                CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                    @Override
                                    public void onSuccess(RESP_Login success) {
                                        getUserFriend();
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        view.requireLogin();
                                        view.showToast("Vui lòng đăng nhập để tiếp tục.");
                                    }
                                });
                            } else if (!TextUtils.isEmpty(error.getMessage())) {
                                view.showToast(error.getMessage());
                            } else {
                                view.showToast(JsonParse.getCodeMessage(error.getCode(), "Có lỗi xảy ra."));
                            }
                        }
                    });
                    break;

                /*
                 * Get Medical from UID*/
                case 2:
                    final int uid = (int) params[1];
                    UserModel.getintance().getMedicalFromUId(uid, LoginManager.getCurrentSession(), new ResponseHandle<RESP_List_Medical>(RESP_List_Medical.class) {
                        @Override
                        public void onSuccess(RESP_List_Medical obj) {
                            view.getMedicalFromUIdSuccess(obj.getList(), uid);
                        }

                        @Override
                        public void onError(Error error) {
                            if (error != null && error.getCode() == 2) {
//                                view.showToast(JsonParse.getCodeMessage(error.getCode(), "Có lỗi."));
                                CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                    @Override
                                    public void onSuccess(RESP_Login success) {
                                        getMedicalFromUserId(uid);
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        view.requireLogin();
                                        view.showToast("Vui lòng đăng nhập để tiếp tục.");
                                    }
                                });
//                                view.requireLogin();
                            } else {
                                if (error != null && error.getMessage() != null) {
                                    view.getMedicalFromUIdError(error.getMessage());
//                                    handlerError(view, error, GetMedicalUid, uid);
                                }
                            }
                        }
                    });
                    break;

                    /*
                    * Get Default Medical*/
                case 3:
                    MedicalDirectoryModel.getinstance().getMedicalReportBooks(LoginManager.getCurrentSession(), new ResponseHandle<RESP_List_Medical>(RESP_List_Medical.class) {
                        @Override
                        public void onSuccess(RESP_List_Medical obj) {
                            xLog.e(TAG, "getMedicalReportBooks: PHIMH" + obj.toString());
                            view.onGetMedicalDefault(obj.getList());
                        }

                        @Override
                        public void onError(Error error) {
                            if (error != null) {
//                                xLog.e(TAG, "getMedicalReportBooks: onError" + error.toString());
                                if (error.getCode() == 2) {
                                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                        @Override
                                        public void onSuccess(RESP_Login success) {
                                            getMedicalReportBookDefault();
                                        }

                                        @Override
                                        public void onError(Error error) {
                                            view.requireLogin();
                                            view.showToast("Vui lòng đăng nhập để tiếp tục.");
                                        }
                                    });
                                }
//                            handlerError(view, error, GETMEDICAL);
                            }
                        }
                    });
                    break;

                case 4:
                    final int friend_uid = (int) params[1];
                    UserModel.getintance().deleteFriendFromUid(friend_uid, LoginManager.getCurrentSession(), new ResponseHandle<RESP_None>(RESP_None.class) {
                        @Override
                        public void onSuccess(RESP_None obj) {
                            view.onDeleteItemSuccess(friend_uid);
                        }

                        @Override
                        public void onError(Error error) {
                            if (error.getCode() == 2) {
//                                handlerError(view, error, DeleteFriend, friend_uid);
                                CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                    @Override
                                    public void onSuccess(RESP_Login success) {
                                        deleteFriend(friend_uid);
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        view.requireLogin();
                                        view.showToast("Vui lòng đăng nhập để tiếp tục.");
                                    }
                                });
                            } else {

                            }
                        }
                    });
                    break;
            }
        }
    };

    public HomePresenter(IHomeView view) {
        this.view = view;
    }

    private void getUser(final Object... param) {
        String url = Constant.SERVER_XMEC + Constant.GET_USER;
        xLog.e(TAG, "getUser: URL  " + url);
        view.onLoadindView();
        UserModel.getintance().getUser(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_User>(RESP_User.class) {
            @Override
            public void onSuccess(RESP_User obj) {
                SharedPreferencesUtils.getInstance().saveUser(obj);
                view.onGetUerSusscess(obj);
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            getUser(GETUSER);
                        }

                        @Override
                        public void onError(Error error) {
                            view.requireLogin();
                            view.showToast("Vui lòng đăng nhập để tiếp tục.");
                        }
                    });
                } else {
                    view.showToast("Có lỗi xảy ra.");
                }
            }
        });
    }

//    public void getMedicalReportBooks(Object... param) {
//        String url = Constant.SERVER_XMEC + Constant.GET_MEDIACAL_REPORT_BOOK;
//        Log.d("URL", "getMedicalReportBooks: " + url);
//        view.onLoadindView();
//        MedicalDirectoryModel.getinstance().getMedicalReportBooks(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_List_Medical>(RESP_List_Medical.class) {
//            @Override
//            public void onSuccess(RESP_List_Medical obj) {
//                xLog.e(TAG, "getMedicalReportBooks: PHIMH" + obj.toString());
//                view.onGetMediacalListSusscess(false, obj);
//            }
//
//            @Override
//            public void onError(Error error) {
//                xLog.e(TAG, "getMedicalReportBooks: onError" + error.toString());
//                handlerError(view, error, GETMEDICAL);
//            }
//        });
//    }

    public void checkGetUser(RESP_User userModel) {
        if (!checkConnnecttion(view))
            return;
        if (SharedPreferencesUtils.getInstance().isLogined()) {
            if (userModel != null) {
                view.onGetUerSusscess(userModel);
            } else {
                getUser(GETUSER);
            }
        } else {
            view.showToast("Vui lòng đăng nhập để có thể sử dụng được tất cả chức năng.");
        }

    }

    public void getMedicalReportBookDefault() {
        if (!checkConnnecttion(view))
            return;
        iCmd.excute(3);
    }

//    public void checkGetMedical(ArrayList<RESP_Medical> mlistMedica) {
//        if (!checkConnnecttion(view))
//            return;
//        if (SharedPreferencesUtils.getInstance().isLogined()) {
//            if (mlistMedica.size() == 0) {
//                getMedicalReportBooks(GETMEDICAL);
//            } else
//                view.onGetMediacalListSusscess(true, new RESP_List_Medical(mlistMedica));
//        } else {
//            view.onGetMediacalListSusscess(true, null);
//        }
//    }

    public void getUserFriend() {
        if (!checkConnnecttion(view)) {
            return;
        }
        iCmd.excute(1);
    }

    public void getMedicalFromUserId(int uid) {
        if (!checkConnnecttion(view)) {
            return;
        }
        iCmd.excute(2, uid);
    }

    public void deleteFriend(int friend_uid) {
        if (!checkConnnecttion(view)) {
            return;
        }
        iCmd.excute(4, friend_uid);
    }


//    public void getMedicalReportBooks() {
//        if (!checkConnnecttion(view)) {
//            return;
//        } else {
//            getMedicalReportBooks(GETMEDICAL);
//        }
//    }
}
