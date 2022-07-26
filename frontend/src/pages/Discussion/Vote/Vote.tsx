import { useEffect, useState } from 'react';
import { MdOutlineHowToVote } from 'react-icons/md';
import { useQuery } from 'react-query';

import { getVoteItems } from '@/api/vote';
import Loading from '@/components/common/Loading/Loading';
import * as S from '@/pages/Discussion/Vote/Vote.styles';
import VoteItem from '@/pages/Discussion/VoteItem/VoteItem';

const Vote = ({ articleId }: { articleId: string }) => {
	const { data, isLoading, isError, isSuccess } = useQuery('vote', () => getVoteItems(articleId));
	const [totalCount, setTotalCount] = useState(0);

	useEffect(() => {
		if (isSuccess) {
			setTotalCount(data.reduce((acc, cur) => acc + cur.count, 0));
		}
	});

	if (isLoading) return <Loading />;

	if (isError) return <div>에러...</div>;

	if (typeof totalCount === 'undefined') {
		throw new Error('데이터를 찾지 못하였습니다');
	}
	return (
		<S.Container>
			<S.VoteTitleBox>
				<S.VoteTitle>투표</S.VoteTitle>
				<S.TotalVotesBox>
					<MdOutlineHowToVote />
					<S.TotalVotes>총 {totalCount}표</S.TotalVotes>
				</S.TotalVotesBox>
			</S.VoteTitleBox>
			<S.VoteBox>
				{data &&
					data.map((datum, idx) => (
						<VoteItem
							key={idx}
							title={datum.option}
							totalVotes={totalCount}
							itemVotes={datum.count}
							name={articleId}
							idx={idx}
						/>
					))}
			</S.VoteBox>
		</S.Container>
	);
};

export default Vote;
