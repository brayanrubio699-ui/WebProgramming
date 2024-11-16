// Store sensitive data in environment variables or a separate secure file.
const GOOGLE_CLIENT_ID = 'YOUR_GOOGLE_CLIENT_ID'; // Replace with your Google Client ID
const FACEBOOK_APP_ID = 'YOUR_FACEBOOK_APP_ID'; // Replace with your Facebook App ID

// Google OAuth Initialization
google.accounts.id.initialize({
  client_id: GOOGLE_CLIENT_ID,
  callback: handleGoogleCredentialResponse,
});

google.accounts.id.renderButton(
  document.getElementById('googleSignInDiv'),
  { theme: 'outline', size: 'large' } // Options for styling
);

function handleGoogleCredentialResponse(response) {
  const idToken = response.credential;
  const userInfo = JSON.parse(atob(idToken.split('.')[1])); // Decode ID Token payload
  document.getElementById('result').innerText =
    `Google Login Successful!\nWelcome, ${userInfo.name} (${userInfo.email})`;
}

// Facebook OAuth Initialization
window.fbAsyncInit = function () {
  FB.init({
    appId: FACEBOOK_APP_ID,
    cookie: true,
    xfbml: true,
    version: 'v15.0', // Use the latest SDK version
  });
};

document.getElementById('facebookLoginButton').addEventListener('click', () => {
  FB.login(
    function (response) {
      if (response.status === 'connected') {
        const accessToken = response.authResponse.accessToken;
        FB.api('/me', { fields: 'name,email' }, function (user) {
          document.getElementById('result').innerText =
            `Facebook Login Successful!\nWelcome, ${user.name} (${user.email})`;
        });
      } else {
        document.getElementById('result').innerText = 'Facebook Login Failed.';
      }
    },
    { scope: 'email,public_profile' }
  );
});

