import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { getAllHashTag } from '@/api/hashTag';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetAllHashTags = () => {
	const { data, isLoading, isError, isSuccess, error } = useQuery<
		{ tag: string[] },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('all-hash-tag', getAllHashTag);

	useThrowCustomError(isError, error);

	return { data, isLoading, isSuccess };
};

export default useGetAllHashTags;
