import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export const postAddLikeArticle = (articleId: string) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.post(`/api/articles/${articleId}/like`, null);
};

export const deleteLikeArticle = (articleId: string) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	return axiosInstance.delete(`/api/articles/${articleId}/like`);
};
