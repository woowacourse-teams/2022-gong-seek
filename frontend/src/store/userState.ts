import { selector } from 'recoil';

export const getUserIsLogin = selector({
	key: 'userLoginState',
	get: () => {
		const accessToken = localStorage.getItem('gongseekAccessToken');
		return !!accessToken;
	},
});
