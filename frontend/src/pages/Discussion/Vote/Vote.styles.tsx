import styled from '@emotion/styled';

export const VoteBox = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_016};
	align-items: center;
`;

export const Container = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_024};

	width: 90%;

	border-radius: ${({ theme }) => theme.size.SIZE_010};

	box-shadow: 0 8px 24px ${({ theme }) => theme.boxShadows.secondary};

	padding: ${({ theme }) => theme.size.SIZE_010};
	margin-top: ${({ theme }) => theme.size.SIZE_026};
`;

export const VoteTitleBox = styled.div`
	display: flex;

	justify-content: space-between;
`;

export const VoteTitle = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_016};
`;

export const TotalVotes = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_010};
`;

export const TotalVotesBox = styled.div`
	display: flex;

	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_004};
`;
