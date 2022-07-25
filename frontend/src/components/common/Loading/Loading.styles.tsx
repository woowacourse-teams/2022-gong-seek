import styled from '@emotion/styled';
import { keyframes } from '@emotion/react';

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
	width: 100%;
	height: 100vh;
	position: relative;
	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const FirstSpinner = styled.div`
	width: ${({ theme }) => theme.size.SIZE_040};
	height: ${({ theme }) => theme.size.SIZE_040};
	border-radius: 50%;
	background-color: ${({ theme }) => theme.colors.PURPLE_400};
	animation: ${oneSpinAnimation} 2s infinite linear;
`;

export const SecondSpinner = styled.div`
	width: ${({ theme }) => theme.size.SIZE_020};
	height: ${({ theme }) => theme.size.SIZE_020};
	border-radius: 50%;
	background-color: ${({ theme }) => theme.colors.ORANGE_500};
	animation: ${twoSpinAnimation} 2s infinite linear;
	transform-origin: -30px 50%;
`;
