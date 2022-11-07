import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';

import { getVoteItems } from '@/api/vote/vote';
import { VoteResponseType } from '@/api/vote/voteType';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useVote = (articleId: string) => {
	const { data, isError, isSuccess, error } = useQuery<
		VoteResponseType,
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

	return { data, totalCount, isSuccess };
};

export default useVote;
