import styled from '@emotion/styled';

export const VoteBox = styled.div`
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_016};
	align-items: center;
`;

export const Container = styled.div`
	width: 60%;
	display: flex;
	flex-direction: column;
	padding: ${({ theme }) => theme.size.SIZE_014};
	box-shadow: 0px 8px 24px ${({ theme }) => theme.boxShadows.primary};
	gap: ${({ theme }) => theme.size.SIZE_024};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
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
