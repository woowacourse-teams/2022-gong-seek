import { getVoteItems } from '@/api/vote';
import { useQuery } from 'react-query';
import VoteItem from '../VoteItem/VoteItem';
import styled from '@emotion/styled';
import { theme } from '@/styles/Theme';
import { MdOutlineHowToVote } from 'react-icons/md';

const VoteBox = styled.div`
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_016};
	align-items: center;
`;

const Container = styled.div`
	width: 60%;
	display: flex;
	flex-direction: column;
	padding: ${({ theme }) => theme.size.SIZE_014};
	box-shadow: 0px 8px 24px ${({ theme }) => theme.boxShadows.primary};
	gap: ${({ theme }) => theme.size.SIZE_024};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
`;

const VoteTitleBox = styled.div`
	display: flex;
	justify-content: space-between;
`;

const Vote = ({ articleId }: { articleId: string }) => {
	const { data, isLoading, isError } = useQuery('vote', getVoteItems);
	const totalCount = data?.reduce((acc, cur) => acc + cur.count, 0);

	if (typeof totalCount === 'undefined') return null;

	if (isLoading) return <div>로딩중...</div>;

	if (isError) return <div>에러...</div>;

	return (
		<Container>
			<VoteTitleBox>
				<h2 css={{ fontSize: theme.size.SIZE_016 }}>투표</h2>
				<div css={{ display: 'flex', alignItems: 'center', gap: theme.size.SIZE_004 }}>
					<MdOutlineHowToVote />
					<p css={{ fontSize: theme.size.SIZE_010 }}>총 {totalCount}표</p>
				</div>
			</VoteTitleBox>
			<VoteBox>
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
			</VoteBox>
		</Container>
	);
};

export default Vote;
