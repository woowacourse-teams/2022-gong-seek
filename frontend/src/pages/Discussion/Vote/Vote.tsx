import { MdOutlineHowToVote } from 'react-icons/md';

import Loading from '@/components/common/Loading/Loading';
import VoteItem from '@/pages/Discussion/VoteItem/VoteItem';

import useGetVote from '@/pages/Discussion/hooks/useGetVote';

import * as S from '@/pages/Discussion/Vote/Vote.styles';

const Vote = ({ articleId }: { articleId: string }) => {
	const { data, isLoading, totalCount } = useGetVote(articleId);

	if (isLoading) return <Loading />;

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
