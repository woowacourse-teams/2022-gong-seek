import { MdOutlineHowToVote } from 'react-icons/md';

import Loading from '@/components/common/Loading/Loading';
import * as S from '@/pages/Discussion/Vote/Vote.styles';
import VoteItem from '@/pages/Discussion/VoteItem/VoteItem';
import useGetVote from '@/pages/Discussion/hooks/useGetVote';

const Vote = ({ articleId }: { articleId: string }) => {
	const { data, isLoading, totalCount } = useGetVote(articleId);

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<S.VoteTitleBox>
				<S.VoteTitle>{`투표(${data?.isExpired ? '만료' : '진행중'})`}</S.VoteTitle>
				<S.TotalVotesBox>
					<MdOutlineHowToVote />
					<S.TotalVotes>총 {totalCount}표</S.TotalVotes>
				</S.TotalVotesBox>
			</S.VoteTitleBox>
			<S.VoteBox>
				{data &&
					data.voteItems.map((datum, idx) => (
						<VoteItem
							voteItemId={datum.id}
							key={datum.id}
							title={datum.content}
							totalVotes={totalCount}
							itemVotes={datum.amount}
							articleId={articleId}
							colorIdx={idx}
							isExpired={data.isExpired}
						/>
					))}
			</S.VoteBox>
		</S.Container>
	);
};

export default Vote;
