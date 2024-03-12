import {
  FacebookAuthProvider,
  GoogleAuthProvider,
  onAuthStateChanged,
  signInWithPopup,
} from 'firebase/auth';
import { useLayoutEffect, useState } from 'react';
import { authentication } from '~/config';
import { useUserStore } from '~/store';

export function useAuth() {
  const { user, setUser } = useUserStore((state) => state);
  const [loading, setLoading] = useState(true);

  useLayoutEffect(() => {
    setLoading(true);

    const unregisterAuthObserver = onAuthStateChanged(authentication, (credential) => {
      if (credential) {
        localStorage.clear();
        const data = credential.providerData[0];
        const firebaseId = data.uid;
        localStorage.setItem('access_token', credential.accessToken);
        setUser({ ...data, firebaseId });
        setLoading(false);
      } else {
        setLoading(false);
      }
    });

    return () => unregisterAuthObserver();
  }, [setUser]);

  const signInWithSocial = async (platform) => {
    setLoading(true);

    let provider = null;
    switch (platform) {
      case 'google':
        provider = new GoogleAuthProvider();
        break;
      case 'facebook':
        provider = new FacebookAuthProvider();
        break;
      default:
        break;
    }

    signInWithPopup(authentication, provider)
      .then(({ user: data }) => {
        // Get Profile API
        console.log('user', data);
      })
      .catch((error) => {
        setLoading(false);
        console.error(error.message);
      });
  };

  return {
    user,
    loading,
    signInWithSocial,
  };
}
