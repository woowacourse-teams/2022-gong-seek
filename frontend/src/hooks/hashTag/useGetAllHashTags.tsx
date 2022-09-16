import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getAllHashTag } from '@/api/hashTag';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';

const useGetAllHashTags = () => {
	const { data, isLoading, isError, isSuccess, error } = useQuery<
		{ tag: string[] },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('all-hash-tag', getAllHashTag);

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [isError]);

	return { data, isLoading, isSuccess };
};

export default useGetAllHashTags;
