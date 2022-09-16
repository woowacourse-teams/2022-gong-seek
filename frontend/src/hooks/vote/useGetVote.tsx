import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState } from 'recoil';

import { getVoteItems, TVote } from '@/api/vote';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { errorPortalState } from '@/store/errorPortalState';

const useVote = (articleId: string) => {
	const { data, isLoading, isError, isSuccess, error } = useQuery<
		TVote,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(['vote', `vote${articleId}`], () => getVoteItems(articleId), {
		retry: false,
		refetchOnWindowFocus: false,
	});
	const [totalCount, setTotalCount] = useState(0);
	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

	useEffect(() => {
		if (isSuccess) {
			setTotalCount(data.voteItems.reduce((acc, cur) => acc + cur.amount, 0));
		}
	});

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

	return { data, isLoading, totalCount, isSuccess };
};

export default useVote;
