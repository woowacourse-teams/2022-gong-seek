import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

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

export const ProgressiveBarContainer = styled.div<{
	width: string;
	height: string;
}>`
	${({ theme, width, height }) => css`
		width: ${width};
		height: ${height};
		border-radius: ${theme.size.SIZE_006};

		background-color: ${theme.colors.GRAY_500};

		margin-left: ${theme.size.SIZE_024};
	`};
`;

export const ProgressiveBarContent = styled.div<{
	percent: number;
	gradientColor: string;
	time: number;
}>`
	height: 100%;
	${({ theme, percent, time, gradientColor }) => css`
		width: ${`${percent}%`};

		border-radius: ${theme.size.SIZE_006};

		background-image: ${gradientColor};

		animation: ${ProgressiveBarAnimation(percent)} ${time}s linear;
	`}
`;
