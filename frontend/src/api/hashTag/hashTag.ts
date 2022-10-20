import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export const getAllHashTag = async () => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<{ tag: string[] }>('/api/tags');
	return data;
};
