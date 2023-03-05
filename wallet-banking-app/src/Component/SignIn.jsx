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
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { useState } from "react";
import { ASYNC_LOGIN, selectUserData } from "../reduxSlices/authSlice";
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
function validatePassword(password) {
  const regex_pass =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!#%*?&]{8,20}$/;
  return regex_pass.test(password);
}

export default function SignIn() {
  const [values, setValues] = useState({
    username: "",
    password: "",
  });
  const dispatch = useDispatch();
  const selectorData = useSelector(selectUserData);
  const error = selectorData.error;
  const logging = selectorData.logging;
  const [passwordError, setPasswordError] = useState(false);

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
    // console.log(values);
  };

  const formSubmitHandler = (event) => {
    event.preventDefault();
    // console.log("Inside form submit");
    let flag = 0;

    if (!validatePassword(values.password)) {
      setPasswordError(true);
      flag = 1;
    } else {
      setPasswordError(false);
    }

    if (flag) return;
    dispatch(
      ASYNC_LOGIN({
        username: values.username,
        password: values.password,
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
            Sign in
          </Typography>
          <Box component="form" noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="username"
              label="Username"
              name="username"
              value={values.username}
              onChange={handleChange("username")}
              autoComplete="username"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              value={values.password}
              onChange={handleChange("password")}
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            {passwordError ? (
              <FormHelperText error={true}>
                Password must have at least 1 number 1 uppercase and lowercase
                character, 1 special symbol and between 8 to 20 characters
              </FormHelperText>
            ) : null}
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
              Sign In
            </Button>
            <Grid container>
              <Grid item xs></Grid>
              <Grid item>
                <Link to="/signup" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  );
}
