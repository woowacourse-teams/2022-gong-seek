import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100vw;
	height: 100vh;
`;

export const Title = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_018};
	color: ${({ theme }) => theme.colors.PURPLE_500};
`;

export const ContentContainer = styled.div`
	display: flex;
	flex-direction: column;
`;
