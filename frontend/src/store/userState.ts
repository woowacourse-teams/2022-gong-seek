import { selector } from 'recoil';

import { ACCESSTOKEN_KEY } from '@/constants';

export const getUserIsLogin = selector({
	key: 'userLoginState',
	get: () => {
		const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
		return !!accessToken;
	},
});
