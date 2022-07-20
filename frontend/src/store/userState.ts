import { selector } from 'recoil';

export const getUserIsLogin = selector({
	key: 'userLoginState',
	get: () => {
		const accessToken = localStorage.getItem('accessToken');
		if (accessToken === null || accessToken.length === 0) return false;
		return true;
	},
});
