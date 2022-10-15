import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const oneSpinAnimation = keyframes`
    0%, 49% {
      transform: scaleX(1);
    }
    50%, 100% {
      transform: scaleX(-1);
    }
  
`;

const twoSpinAnimation = keyframes`
100% {
      transform: rotate(360deg);
    }
`;

export const Container = styled.section`
	display: flex;
	position: relative;

	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_010};

	width: 100%;
	height: 70vh;
`;

export const FirstSpinner = styled.div`
	border-radius: 50%;

	${({ theme }) => css`
		width: ${theme.size.SIZE_040};
		height: ${theme.size.SIZE_040};

		background-color: ${theme.colors.PURPLE_400};

		animation: ${oneSpinAnimation} 2s infinite linear;
	`}
`;

export const SecondSpinner = styled.div`
	border-radius: 50%;

	${({ theme }) => css`
		width: ${theme.size.SIZE_020};
		height: ${theme.size.SIZE_020};
		background-color: ${theme.colors.ORANGE_500};

		transform-origin: -${theme.size.SIZE_030} 50%;

		animation: ${twoSpinAnimation} 2s infinite linear;
	`}
`;
