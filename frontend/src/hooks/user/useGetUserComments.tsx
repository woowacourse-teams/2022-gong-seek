import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState } from 'recoil';

import { getUserComments } from '@/api/myPage';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { errorPortalState } from '@/store/errorPortalState';
import { UserCommentResponse } from '@/types/commentResponse';

const useGetUserComments = () => {
	const { data, isSuccess, isLoading, isIdle, isError, error } = useQuery<
		UserCommentResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('user-comments', getUserComments, { retry: 1, refetchOnWindowFocus: false });

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

	return {
		data,
		isSuccess,
		isLoading,
		isIdle,
	};
};

export default useGetUserComments;
