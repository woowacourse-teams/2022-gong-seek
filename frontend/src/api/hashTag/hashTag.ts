import { HashTagResponseType } from '@/api/hashTag/hashTagType';
import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export const getAllHashTag = async () => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<HashTagResponseType>('/api/tags');
	return data;
};
