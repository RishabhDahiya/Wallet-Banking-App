import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { Link } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useDispatch, useSelector } from "react-redux";
import { useState } from "react";
import { ASYNC_SIGNUP, selectUserData } from "../reduxSlices/authSlice";
import FormHelperText from "@mui/material/FormHelperText";
import CircularProgress from "@mui/material/CircularProgress";

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="https://mui.com/">
        Your Website
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const theme = createTheme();

function validateEmail(email) {
  // console.log(email);
  const re =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(String(email).toLowerCase());
}

function validatePassword(password) {
  const regex_pass =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!#%*?&]{8,20}$/;
  return regex_pass.test(password);
}

export default function SignUp() {
  const [values, setValues] = useState({
    email: "",
    username: "",
    password: "",
  });
  const dispatch = useDispatch();
  const selectorData = useSelector(selectUserData);
  const error = selectorData.error;
  const logging = selectorData.logging;
  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
    console.log(values);
  };

  const formSubmitHandler = (event) => {
    event.preventDefault();
    // console.log("Inside form submit");
    let flag = 0;

    if (!validateEmail(values.email)) {
      setEmailError(true);
      flag = 1;
    } else {
      setEmailError(false);
    }

    if (!validatePassword(values.password)) {
      setPasswordError(true);
      flag = 1;
    } else {
      setPasswordError(false);
    }

    if (flag) return;
    dispatch(
      ASYNC_SIGNUP({
        email: values.email,
        password: values.password,
        username: values.username,
        logging: true,
      })
    );
    
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign up
          </Typography>
          <Box component="form" noValidate sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  autoComplete="given-name"
                  name="username"
                  value={values.username}
                  onChange={handleChange("username")}
                  required
                  fullWidth
                  id="username"
                  label="Username"
                  autoFocus
                />
              </Grid>

              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  name="email"
                  value={values.email}
                  onChange={handleChange("email")}
                  autoComplete="email"
                />
                {emailError ? (
                  <FormHelperText error={true}>
                    Enter a valid Email ID
                  </FormHelperText>
                ) : null}
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  value={values.password}
                  onChange={handleChange("password")}
                  label="Password"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                />
              </Grid>
              {passwordError ? (
                <FormHelperText error={true}>
                  Password must have at least 1 number 1 uppercase and lowercase
                  character, 1 special symbol and between 8 to 20 characters
                </FormHelperText>
              ) : null}
            </Grid>
            {logging ? (
              <CircularProgress className="display-block" />
            ) : error ? (
              <FormHelperText error={true}>{error}</FormHelperText>
            ) : null}
            <Button
              type="submit"
              fullWidth
              variant="contained"
              onClick={formSubmitHandler}
              sx={{ mt: 3, mb: 2 }}
            >
              Sign Up
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link to="/signin" variant="body2">
                  Already have an account? Sign in
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 5 }} />
      </Container>
    </ThemeProvider>
  );
}
