import { getVoteItems } from '@/api/vote';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';
import { AxiosError } from 'axios';
import { VoteItems } from '@/api/vote';

const useVote = (articleId: string) => {
	const { data, isLoading, isError, isSuccess, error } = useQuery<VoteItems[], AxiosError>(
		'vote',
		() => getVoteItems(articleId),
	);
	const [totalCount, setTotalCount] = useState(0);

	useEffect(() => {
		if (isSuccess) {
			setTotalCount(data.reduce((acc, cur) => acc + cur.count, 0));
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	return { data, isLoading, totalCount };
};

export default useVote;
