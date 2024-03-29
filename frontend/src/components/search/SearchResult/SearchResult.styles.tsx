import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100vw;

	padding: ${({ theme }) => theme.size.SIZE_010};
	gap: ${({ theme }) => theme.size.SIZE_020};
`;

export const Title = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_018};
`;
