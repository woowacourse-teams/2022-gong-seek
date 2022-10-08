import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';

import { getVoteItems, TVote } from '@/api/vote';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useVote = (articleId: string) => {
	const { data, isLoading, isError, isSuccess, error } = useQuery<
		TVote,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(['vote', `vote${articleId}`], () => getVoteItems(articleId), {
		retry: false,
		refetchOnWindowFocus: false,
	});
	const [totalCount, setTotalCount] = useState(0);

	useEffect(() => {
		if (isSuccess) {
			setTotalCount(data.voteItems.reduce((acc, cur) => acc + cur.amount, 0));
		}
	});

	useThrowCustomError(isError, error);

	return { data, isLoading, totalCount, isSuccess };
};

export default useVote;
