import { voteGradientColors } from '@/styles/Theme';
import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

export const RadioButton = styled.input`
	margin: 0;
`;

export const Title = styled.h2<{ isVoted: boolean; expired: boolean }>`
	display: flex;
	align-items: center;

	${({ theme, isVoted, expired }) => css`
		gap: ${theme.size.SIZE_004};
		font-size: ${theme.size.SIZE_014};

		color: ${isVoted
			? `${theme.colors.BLUE_500}`
			: expired
			? theme.colors.BLACK_300
			: theme.colors.BLACK_600};
	`}
`;

export const TitleBox = styled.div`
	display: flex;

	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_014};
`;

export const ProgressiveBarAnimation = (percent: number) => keyframes`
	0% {
		width: 0;
		background-position: 100% 50%;
	}

	50% {
		background-position: 0% 50%;
	}

	100% {
		width: ${percent}%;
		background-position: 0% 50%;
	}
`;

export const ProgressiveBar = styled.div`
	${({ theme }) => css`
		width: ${theme.size.SIZE_170};
		height: ${theme.size.SIZE_010};

		border-radius: ${theme.size.SIZE_006};

		background-color: ${theme.colors.GRAY_500};

		margin-left: ${theme.size.SIZE_024};
	`};
`;

export const ProgressiveBarContent = styled.div<{
	percent: number;
	colorKey: keyof typeof voteGradientColors;
}>`
	height: 100%;
	${({ theme, percent, colorKey }) => css`
		width: ${`${percent}%`};

		border-radius: ${theme.size.SIZE_006};

		background-image: ${theme.voteGradientColors[colorKey]};

		animation: ${ProgressiveBarAnimation(percent)} 1.2s cubic-bezier(0.23, 1, 0.32, 1);
	`}
`;

export const Container = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_014};
`;

export const ItemVotes = styled.p`
	font-size: ${({ theme }) => theme.size.SIZE_010};

	color: ${({ theme }) => theme.colors.PURPLE_400};
`;
