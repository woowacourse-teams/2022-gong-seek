import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState } from 'recoil';

import { getAllHashTag } from '@/api/hashTag';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { errorPortalState } from '@/store/errorPortalState';

const useGetAllHashTags = () => {
	const { data, isLoading, isError, isSuccess, error } = useQuery<
		{ tag: string[] },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('all-hash-tag', getAllHashTag);

	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

	useEffect(() => {
		if (isError) {
			if (!error.response?.data?.errorCode) {
				setErrorPortal({ isOpen: true });
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response?.data.errorCode],
			);
		}
	}, [isError]);

	return { data, isLoading, isSuccess };
};

export default useGetAllHashTags;
