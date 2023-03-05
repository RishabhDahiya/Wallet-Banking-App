import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { BASE_URL } from "../api/constants";

const authSlice = createSlice({
  name: "auth",
  initialState: {
    token: null,
    username: null,
    email: null,
    error: null,
    loading: true,
    logging: false,
  },
  reducers: {
    SET_LOGGING: (state, action) => {
      state.logging = action.payload;
    },

    SET_LOADING: (state, action) => {
      state.loading = action.payload;
    },

    SET_ERROR_NULL: (state, action) => {
      state.error = null;
    },

    SET_ERROR: (state, action) => {
      state.error = action.payload;
    },

    LOGIN: (state, action) => {
      state.token = action.payload.token;
      state.username = action.payload.username;
      state.email = action.payload.email;
    },

    LOGOUT: (state, action) => {
      state.token = null;
      state.userId = null;
      state.email = null;
      state.username = null;
      localStorage.removeItem("Wallet__token");
      localStorage.removeItem("Wallet__username");
      localStorage.removeItem("Wallet__email");
    },
  },
});

export const {
  LOGIN,
  LOGOUT,
  SET_ERROR,
  SET_ERROR_NULL,
  SET_LOADING,
  SET_LOGGING,
} = authSlice.actions;

export const AUTOLOGIN = () => async (dispatch) => {
  // console.log("Hello");
  dispatch(SET_LOADING(true));
  const token = localStorage.getItem("Wallet__token");
  // Will verify bearer jwt token with backend
  if (token) {
    const userId = localStorage.getItem("Wallet__userId");
    const username = localStorage.getItem("Wallet__username");
    const email = localStorage.getItem("Wallet__email");
    dispatch(
      LOGIN({
        token: token,
        userId: userId,
        username: username,
        email: email,
      })
    );

    dispatch(SET_LOADING(false));
  } else dispatch(SET_LOADING(false));
};

export const ASYNC_LOGIN = (userData) => (dispatch) => {
  dispatch(SET_LOGGING(true));
  dispatch(SET_ERROR_NULL());
  const authData = {
    username: userData.username,
    password: userData.password,
  };
  let URL = BASE_URL + "/api/auth/login";
  axios
    .post(URL, authData)
    .then((response) => {
      const token = response.data.token;
      const email = response.data.email;
      const username = response.data.username;
      localStorage.setItem("Wallet__token", token);
      localStorage.setItem("Wallet__email", email);
      localStorage.setItem("Wallet__username", username);
      dispatch(
        LOGIN({
          token: token,
          username: username,
          email: email,
        })
      );
      dispatch(SET_LOADING(false));
      dispatch(SET_LOGGING(false));
    })
    .catch((err) => {
      if (err.response && err.response.data) {
        console.log(err.response.data.message);
        dispatch(SET_ERROR(err.response.data.message));
      }
      dispatch(SET_LOADING(false));
      dispatch(SET_LOGGING(false));
    });
};

export const ASYNC_SIGNUP = (authData) => (dispatch) => {
  dispatch(SET_LOGGING(true));
  dispatch(SET_ERROR_NULL());
  let URL = BASE_URL + "/api/auth/signup";
  axios
    .post(URL, authData)
    .then((response) => {
      console.log(response);
      // localStorage.setItem('Wallet__email', response.data.email);
      // localStorage.setItem('Wallet__username', response.data.username);
      // dispatch(LOGIN({
      //   token: token,
      //   username: username,
      //   email: email
      // }))
      dispatch(SET_LOADING(false));
      dispatch(SET_LOGGING(false));
    })
    .catch((err) => {
      if (err.response && err.response.data) {
        console.log(err.response.data.message);
        alert(err.response.data.message);
        dispatch(SET_ERROR(err.response.data.message));
      }
      dispatch(SET_LOADING(false));
      dispatch(SET_LOGGING(false));
    });
};

export const selectUserData = (state) => state.auth;

export default authSlice.reducer;
