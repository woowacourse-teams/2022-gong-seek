export const deleteRefreshCookie = () => {
	if (!document.cookie) {
		return;
	}
	document.cookie = 'refreshToken=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
	return;
};
