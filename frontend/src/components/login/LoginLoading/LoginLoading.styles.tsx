import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const logoRotateAnimation = keyframes`
    100%{
        transform: rotate(360deg);
    }
`;

export const Container = styled.section`
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;

	width: 100%;
	height: 80vh;
`;

export const LogoImg = styled.img`
	width: ${({ theme }) => theme.size.SIZE_160};
	height: ${({ theme }) => theme.size.SIZE_160};

	animation: ${logoRotateAnimation} 2s infinite linear;
`;

export const Message = styled.div`
	margin-top: ${({ theme }) => theme.size.SIZE_040};
	size: ${({ theme }) => theme.size.SIZE_018};
`;
