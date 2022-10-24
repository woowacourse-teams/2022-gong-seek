import {
	DetailTempArticleResponseType,
	TotalTempArticleResponseType,
} from '@/api/tempArticle/tempArticleType';
import { postTempArticleProps } from '@/hooks/tempArticle/usePostTempArticle';
import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export const getTempArticles = async () => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<TotalTempArticleResponseType>(`/api/temp-articles`);
	return data;
};

export const getTempDetailArticle = ({ id }: { id: number | '' }) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.get<DetailTempArticleResponseType>(`/api/temp-articles/${id}`);
};

export const postTempArticle = ({ ...props }: postTempArticleProps) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.post<{ id: number }>('/api/temp-articles', { ...props });
};

export const deleteArticleItem = ({ tempArticleId }: { tempArticleId: number }) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.delete<never, unknown, unknown>(`/api/temp-articles/${tempArticleId}`);
};
