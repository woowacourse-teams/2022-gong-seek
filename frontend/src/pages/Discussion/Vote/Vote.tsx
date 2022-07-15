import { getVoteItems } from '@/api/vote';
import { useQuery } from 'react-query';
import VoteItem from '@/pages/Discussion/VoteItem/VoteItem';
import { MdOutlineHowToVote } from 'react-icons/md';
import { useEffect } from 'react';
import * as S from '@/pages/Discussion/Vote/Vote.styles';

const Vote = ({ articleId }: { articleId: string }) => {
	const { data, isLoading, isError, isSuccess } = useQuery('vote', getVoteItems);
	let totalCount = 0;

	useEffect(() => {
		if (isSuccess) {
			totalCount = data.reduce((acc, cur) => acc + cur.count, 0);
		}
	}, []);

	if (isLoading) return <div>로딩중...</div>;

	if (isError) return <div>에러...</div>;

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
							name={`vote-${articleId}`}
							idx={idx}
						/>
					))}
			</S.VoteBox>
		</S.Container>
	);
};

export default Vote;
