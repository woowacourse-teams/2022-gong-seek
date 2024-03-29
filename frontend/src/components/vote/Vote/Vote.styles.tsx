import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const VoteBox = styled.div`
	display: flex;

	margin: 0 auto;
	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_016};
	align-items: flex-start;
`;

export const Container = styled.div`
	display: flex;

	flex-direction: column;

	width: 90%;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_024};
		border-radius: ${theme.size.SIZE_010};

		box-shadow: 0 ${theme.size.SIZE_008} ${theme.size.SIZE_024} ${theme.boxShadows.secondary};

		padding: ${theme.size.SIZE_010};
		margin-top: ${theme.size.SIZE_026};
	`}
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
