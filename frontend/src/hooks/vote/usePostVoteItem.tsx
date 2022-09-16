import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useRecoilState } from 'recoil';

import { checkVoteItems } from '@/api/vote';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { queryClient } from '@/index';
import { errorPortalState } from '@/store/errorPortalState';

const usePostVoteItem = (articleId: string) => {
	const { isError, error, mutate, isSuccess, isLoading } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ articleId: string; voteItemId: string }
	>(checkVoteItems);
	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

	useEffect(() => {
		if (isSuccess) {
			queryClient.invalidateQueries(['vote', `vote${articleId}`]);
		}
	}, [isSuccess]);

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

	const onChangeRadio = (articleId: string, idx: number) => {
		mutate({ articleId, voteItemId: String(idx) });
	};

	return { onChangeRadio, isLoading, isSuccess };
};

export default usePostVoteItem;
