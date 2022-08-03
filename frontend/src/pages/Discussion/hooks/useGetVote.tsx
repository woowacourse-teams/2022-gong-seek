import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';

import { getVoteItems, TVote } from '@/api/vote';

const useVote = (articleId: string) => {
	const { data, isLoading, isError, isSuccess, error } = useQuery<TVote, AxiosError>(
		['vote', `vote${articleId}`],
		() => getVoteItems(articleId),
	);
	const [totalCount, setTotalCount] = useState(0);

	useEffect(() => {
		if (isSuccess) {
			setTotalCount(data.voteItems.reduce((acc, cur) => acc + cur.amount, 0));
		}
	});

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	return { data, isLoading, totalCount };
};

export default useVote;
